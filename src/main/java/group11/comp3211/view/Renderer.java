package group11.comp3211.view;

public final class Renderer {
	private Renderer() {
	}

	public static Renderer getInstance() {
		return RendererHolder.RENDERER;
	}

	public String renderChessBoard() {
		return null;
	}

	public String renderAnnouncementBoard() {
		return null;
	}

	private static final class RendererHolder {
		private static final Renderer RENDERER = new Renderer();
	}
}
